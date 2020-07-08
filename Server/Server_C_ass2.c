#include <stdio.h>
#include <stdlib.h>
#include <crypt.h>
#include <netdb.h>
#include <netinet/in.h>
#include <string.h>
#include <syslog.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <errno.h>
#include <time.h>
#include <sys/time.h>
/*#include <capture.h>*/
#include <sys/socket.h>
#include <arpa/inet.h>


char* sendAdress(char *s1, char *s2)
	{
	// malloc function returns a pointer to the allocated memory, or NULL if the request fails.
	    char *result = malloc(strlen(s1)+strlen(s2)+1);//string length 
	    strcpy(result, s1); // strcpy returns a pointer to the destination string dest.
	    strcat(result, s2);
	    return result;
	}

char* getResolution(int r) {
	switch (r) {
		case 0:
			return "160x90";
		case 1:
			return "320x240";
		case 2:
			return "480x270";
		case 3:
			return "640x480";

	}
	return "320x240";
	}

char* getFps(int f) {
	switch (f) {
		case 0:
			return "1";
		case 1:
			return "2";
		case 2:
			return "3";
		case 3:
			return "4";
		case 4:
			return "5";
		case 5:
			return "6";
		case 6:
			return "7";
		case 7:
			return "8";
		case 8:
			return "9";
		case 9:
			return "10";
		case 10:
			return "11";
		case 11:
			return "12";
		case 12:
			return "13";
		case 13:
			return "14";
		case 14:
			return "15";
		case 15:
			return "16";

	}
	return "2";
	}



int main(void) {
	media_frame  *frame;
	void     *data;
	//char buffer[256];
	media_stream *stream;
	int sockfd, newsockfd, portno, clilen;
   	struct sockaddr_in serv_addr, cli_addr;
   	int  pid;

	//openlog(APP_NAME,LOG_PID,LOG_LOCAL4);
	/* First call to socket() function */
	   sockfd = socket(AF_INET, SOCK_STREAM, 0);
	   if (sockfd < 0) {
	      perror("ERROR opening socket");
	      exit(1);
	   }



	/* Initialize socket structure */
	   bzero((char *) &serv_addr, sizeof(serv_addr));
	   portno = 5005;
	   serv_addr.sin_family = AF_INET;
	   serv_addr.sin_addr.s_addr = INADDR_ANY;
	   serv_addr.sin_port = htons(portno);
	   

	/* Now bind the host address using bind() call.*/
	   if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
	      perror("ERROR on binding");
	      exit(1);
	   }

	// Print message on log 
	  syslog(LOG_INFO,"\n bind the host address using bind");
	      

	/* Now start listening for the clients, here process will go in sleep mode and will wait for the incoming connection*/
	   
	   listen(sockfd,5);
	   clilen = sizeof(cli_addr);
	  
	/* Accept actual connection from the client */
	  while (1) {
	   newsockfd = accept(sockfd, (struct sockaddr *)&cli_addr, &clilen);

	   syslog(LOG_INFO,"Accept actual connection\n");
	
	   if (newsockfd < 0) {
	      perror("ERROR on accept");
	      exit(1);
	   }

 /* Create child process */
      pid = fork();
		
      if (pid < 0) {
         perror("ERROR on fork");
         exit(1);
      }
      
      if (pid == 0) {
         /* This is the client process */
        char ipBuffer[14]; 
	char resBuffer[1];
	char fpsBuffer[1];
	
	
	read(newsockfd,&ipBuffer,14);
	read(newsockfd,&resBuffer,1);
	read(newsockfd,&fpsBuffer,1);
	
	char* ipaddress = &ipBuffer;
	char* resolution = getResolution(atoi(&resBuffer));
	char* fps=getFps(atoi(&fpsBuffer));

	
	char* parameters = sendAdress("capture-cameraIP=",ipaddress);

	char* parameters1 = sendAdress(parameters,"&capture-userpass=root:pass&resolution=");
	char* parameters2 = sendAdress(parameters1,resolution);
	char* parameters4 = sendAdress(parameters2,"&fps=25");

	

// Print message on logo 
	syslog(LOG_INFO," sending the images \n");
	syslog(LOG_INFO, parameters);
	syslog(LOG_INFO, parameters2);
	syslog(LOG_INFO, parameters4);
	syslog(LOG_INFO, resolution);
	syslog(LOG_INFO, fps);
	stream = capture_open_stream(IMAGE_JPEG, parameters4);
	free(parameters1);
	free(parameters2);
	close(sockfd);



		while (1) {		
		 syslog(LOG_INFO,"take pic\n");
		frame  = capture_get_frame(stream);
		data   = capture_frame_data(frame);
		size_t size   = capture_frame_size(frame);
		char str[6];
		snprintf(str, sizeof str, "%zu", size);
		syslog(LOG_INFO,"take pic1\n");
		int row= 0;
		
		unsigned char rowData[size];
		


		// read the data for image row by row 
		for (row=0 ; row< size ; row++)
		{
			rowData[row]=((unsigned char*)data)[row];		
		}
		

		
		// first send size to client :  write and send the  size to the client 

		write(newsockfd, str, sizeof(str));
		syslog(LOG_INFO,"here is size %d",size);
			syslog(LOG_INFO,"here is str %s",str);


		// send the image data
		
		write(newsockfd, rowData, sizeof(rowData));
		capture_frame_free(frame);
		//sleep 		
		sleep(atoi(&fpsBuffer)+1);
		syslog(LOG_INFO,"next picture \n");

			}
	capture_close_stream(stream);
 	exit(0);
      	}
      else {
         close(newsockfd);
      }/* end of while */
		
   } 
}
