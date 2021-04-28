import time
import random

input1 = random.randint(1,30)
nowid = 1
nowtime = 0
maxtime =130
f= open("input.txt","w")
#print(input1)
while(nowid<=input1):
    from1 = random.randint(1,15)
    to = random.randint(1,15)
    while(to==from1):
        to = random.randint(1,15)
    f.write("[",)
    f.write("%.1f"%nowtime)
    f.write("]")
    f.write(str(nowid))
    f.write("-FROM-")
    f.write(str(from1))
    f.write("-TO-")
    f.write(str(to))
    f.write("\n")
    nowid = nowid+1
    a = random.uniform(0,min(maxtime-nowtime,30))
    b = round(a,1)
    #print(b)
    nowtime += b
f.close()
