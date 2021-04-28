#include<stdio.h>
#include<string.h>

char s[500];
double ss; 
char s1[500];
char s2[50][500];
int main()
{
	int temp =0;
	double nowtime = 0.0;
	//freopen("out.txt","w",stdout);
	printf("import subprocess\nimport time\nimport threading\n");
	printf("ff = open(\"output.txt\",\"w\")\n");
	printf("output1 = subprocess.Popen(\"java -jar G:\\compus\\OO\\homework_6\\");
	printf("homework_6_better1.jar\",stdin = subprocess.PIPE,stdout = ff,shell=True)\n");
	while(scanf("%s",s)!=EOF){
		//("%s",s);
		int len = strlen(s);
		int flag = 0;
		int flag1 = 0;
		int num = 0;
		int num2=0;
		ss=0;
		int i;
		for(i = 0; i<len;i++) {
			if (flag ==0){
			if(s[i]=='['){
				ss = 0;
			}
			else if (s[i]==']'){
				flag = 1;
			}
			else if(s[i]>='0'&&s[i]<='9'){
				ss = ss*10+s[i]-'0';
				if(flag1==1){
					num++;
				}
			}
			else {
				flag1 = 1;
			}
			}
			else{
				s1[num2++] = s[i];
			}
		} 
		ss = ss/(num*10);
		if(temp!=0){
		printf("time.sleep(%.1lf)\n",ss-nowtime);
		}
		printf("output1.stdin.write(b\"");
		s1[num2]='\0';
		printf("%s",s1);
		printf("\\n\")\n");
		printf("output1.stdin.flush()\n");
		nowtime = ss;
		temp++;
	}
	printf("output1.stdin.close()\n");
	printf("ff.close()\n"); 
	//fclose(stdout);
  return 0;
}


