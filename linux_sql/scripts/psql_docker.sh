#!/bin/bash
# This script will automate run/start/stop of a psql docker container and also set up volume to save data
# Check if docker is running in the system if not it will start docker
sudo systemctl status docker || systemctl start docker

# Get the line count of all containers currently and store it in a variable
container_status=$(docker ps -a -f name=jrvs-psql | wc -l)
pg_usage=$1

# Handle the first argument accordingly using case statements
case pg_usage in
  # When first argument is "create" handle it.
  "create")
    # If container_status is 2 then container is already created. Exit.
    if [ $container_status -eq 2 ]; then
      echo "Error: The container 'jrvs-psql' is already created."
      exit 0
    fi

    # If number of following arguments are wrong, then arguments are not fully provided. Exit.
    if [ $# -ne 3 ]; then
        echo "'Error: Provide both 'db_username' and 'db_password' as input arguments."
        exit 1
    fi

    # Export the user name and password to the environment variables so user can connect to psql
    # instance without prompting password
    export PGUSERNAME=$2
    export PGPASSWORD=$3

    # Allocate a volume to persist data during container run-time
    # Create the specified jrvs-psql container with all the options necessary. Exit
    docker volume create pgdata
    docker create --name jrvs-psql -e POSTGRES_USER=$PGUSERNAME -e POSTGRES_PASSWORD=$PGPASSWORD -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres
    exit $?
    ;;

  # If first argument is "start" or "stop", handle it here
  "start" | "stop")
    # If line count is not 2, then container instance was not created. Exit
    if [ $container_status -ne 2 ]; then
      echo "Error: The container 'jrvs-psql' is not created"
      exit 1
    fi

    case pg_usage in
    "start")
      # If first argument == "start", then start the container
      docker container start jrvs-psql
      exit $?
      ;;

    "stop")
      # If first argument == "stop", then stop the container
      docker container stop jrvs-psql
      exit $?
      ;;
    esac
    ;;

  # default condition
  *)
    # If none of the case values are equal to the expression, then wrong argument. Exit
    echo "Error: Argument Invalid: Please provide correct arguments 'start|stop|create'"
    exit 1
    ;;
esac