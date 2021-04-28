import time
import random

nowid = 1
nowtime = 1
maxtime =40
f= open("input.txt","w")
#print(input1)
f.write("[0.0]")
ele =  random.randint(1,5)
input1 = random.randint(1,min(30+10*ele,50))
#input1 = 50
f.write(str(ele))
f.write("\n")
while(nowid<=input1):
    from1 = random.randint(-3,16)
    while from1 == 0:
        from1 = random.randint(-3,16)
    to = random.randint(-3,16)
    while to==from1 or to ==0 :
        to = random.randint(-3,16)
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
    a = random.uniform(0,min(maxtime-nowtime,2))
    b = round(a,1)
    #print(b)
    nowtime += b
f.close()
