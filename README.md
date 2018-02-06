# Sample java project for Connectfour game with junit test cases.

Sample java project with junit test cases, to configure continous testing and building in jenkins, monitoring git remote repository, running test cases, building source code and deploying packages to artifacts storage repo/to tomcat web container, when new commits are made.

## File structure

    .
    ├── jobsetup               # folder contains images for build job configuration
    ├── lib                    # libraries folder containing jnit library
    ├── src 				   
	│    ├── main              # folder containing java files
    │    └── test              # folder containing test cases java files
    ├── .gitignore 	           # file that contains info about other files and folders to be ignored by git in this repository
    ├── build.xml			   # ant build file 
    └── pom.xml                # maven build file 
	
# Steps to install and configure jenkins with docker (for RHEL/CentOS 7)

* Install docker package (all commands prompted as super doer to avoid access concerns).

  ```sudo yum install docker -y```
* Start docker daemon

  ```sudo systemctl start docker```
* To check if docker is up and running

  ```sudo systeamctl status docker```
  
  ```sudo docker info```
  
  ```sudo docker run hello-world```
* Search and Pull jenkins image from docker repository

  ```sudo docker search jenkins```
  
  ```sudo docker pull jenkins```
* Run docker container pulled jenkins image on port 8080, for host and image.

  ```sudo docker run -p 8080:8080 jenkins```
* Open localhost:8080 in a web browser, use above password command to retrieve initial password and follow steps for complete installation of jenkins.

  ```sudo docker ps   -   to get jenkins cotainer Id```
  
  ```sudo docker exec -it <conatiner_id> cat /var/jenkins_home/secrets/initialAdminPassword```
* Install ant and maven on jenkins master node using below commands

  ```sudo docker exec -it --user root <conatiner_id>  apt-get update```
  
  ```sudo docker exec -it --user root <conatiner_id>  apt-get install ant maven -y```

# Steps to configure continous build job (jobsetup folder has screenshots for same).

* Step 1 - Create new job selecting free style project
* Step 2 - Select git as source code management tool and provide git repo url.
* Step 3 - Select Poll SCM as build trigger option and schedule poll every minute (* * * * *)
* Step 4 - Select invoke ant and top level maven tarkets as Build options. 
		   Targets not required for ant as default is specified in script, prompt goals for maven (clean package - are recommended)
* Step 5 - Artifacts deployer, email notification can selected as post build action. 
           (Plugin need to be installed for artifact deployer and smtp authentication need tobe configured for email notification)
* Artifact can be deployed to tomcat container (once configued) as post build action.
		   
[This image](jobsetup/1.png) has screenshot for job configuration

[This image](jobsetup/2.png) has plugins tobe installed for post build actions.

# View for next steps of this solution.

This solution can be configured through jenkins job DSL that uses Gradle for building and testing. Also docker containers can ne run, deployed and destroyed once image is pushed to docker registry through jenkins.
