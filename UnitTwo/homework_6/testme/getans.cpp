#include<stdio.h>
#include<string.h>

char s[500];
int main()
{
	int temp;
	int temp1;
	double ans;
	while((gets(s))) {
		double ss= 0;
		int len = strlen(s);
		int flag = 0;
		int flag1 = 0;
		int num = 1;
		int num2=0;
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
					num*=10;
				}
			}
			else {
				flag1 = 1;
			}
			}
		} 
		ss = ss/(10000);
		if(ss!=0){
		ans = ss;
	}	
	}
	printf("%.4lf\n",ans);
  return 0;
}


