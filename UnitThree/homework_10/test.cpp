#include<bits/stdc++.h>
using namespace std;
int main()
{
	int id =1;
	int temp = 0;
	while(temp<2000){
		temp++;
		printf("ap %d 1 1 1\n",id);
		id++;
	} 
	temp = 1;
	while(temp<2000){
		printf("ar %d %d 100\n",temp,temp+1);
		temp++;
	}
	temp =0 ;
	while(temp<333){
		temp++;
		printf("qci 1 2000\n");
	}
  return 0;
}


