#include<bits/stdc++.h>
using namespace std;
int main()
{
	int alld = 1000;
	for (int i = 0; i < 800; i++) {
		printf("ap %d 1 1 1\n",i);
	}
	int alledge = rand()%2000;
	for (int i = 0; i < 2000;i++){
		int u = rand()%alld;
		int v = rand()%alld;
		int len = rand()%100;
		printf("ar %d %d %d\n",u,v,len);
	}
	for (int i = 0;i<800;i++){
		int u = rand()%alld;
		int v = rand()%alld;
		printf("qsl %d %d\n",u,v);
	}
  return 0;
}


