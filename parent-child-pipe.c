#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>


int main() {

     pid_t   pid; // process için tanımlama
     int communication1[2]; // parent process'ın child Proces ile iletişim kurması için bir dizi
     int communication2[2];// child process'in parent process ile iletişim kruması için bir dizi
     long int number,reverseNumber=0; //tersi alınacak sayı ve ters çevrilmiş sayı
     int remainder=0;//sayıyı ters çevirirken kullanılacakkalan değişkeni
  
     pipe(communication1); // Parent'tan Child'a bir iletişim borusu oluşturuldu
     pipe(communication2); // Child'dan Parent'a bir iletişim borusu oluşturuldu 

     pid=fork();//child process oluşturuldu
     if (pid < 0) { // error kontrolü 
          printf("Failed to fork process 1\n");
          exit(1);
     }
    
     else if (pid!=0) { // parent process
	         printf("parent id is %d\n",getppid());
             printf("\nPlease enter a number : ");
             scanf("%ld",&number);

	    close(communication1[0]); // yazma işlemi için parent'ın okuma hattı kapatıldı
	    write(communication1[1],&number,sizeof(number));//parent'ın yazma işlemi hattı çalışıyor
        printf("parent send the number to child\n");
	    close(communication1[1]); // parent'ın yazma işlemi hattı kapatıldı

             printf("\nchild received the number from parent\n"); 
             printf("child id is %d\n\n",getpid());  

        

             printf("the number was reversed\n");
        close(communication2[1]);// child'ın yazma işlemi hattı kapatıldı
        read(communication2[0],&reverseNumber,sizeof(reverseNumber));//child'ın okuma işlemi hattı çalışıyor

             printf("reverse of number : %ld\n",reverseNumber); //ters çevrilmiş sayıyı ekrana bastı.
 
        close(communication2[0]);//child'ın okuma işlemi hattı kapatıldı

    }    

     else if(pid == 0) { // child process
            
         close(communication1[1]);//parent'ın yazma işelmi hattı kapatıldı 
         read(communication1[0], &number, sizeof(number));//parent'ın okuma işlemi hattı çalışıyor 
         close(communication1[0]);// parent'ın okuma işlemi hattı kapatıldı.
   
	            while(number!=0){ //sayıyı ters işlemi 

                    remainder=number%10;
   			        reverseNumber=reverseNumber*10+remainder;
                    number/=10;
    
                }
          close(communication2[0]); // child'ın  okuma işlemi hattı kapatıldı
          write(communication2[1],&reverseNumber,sizeof(reverseNumber));//child'ın okuma işlemi hattı çalışıyor
          close(communication2[1]); // child'ın yazma işlemi hattı kapatıldı  

          exit(1);
   
     }

return 0;

}


