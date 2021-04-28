import time
import random

nowid = 1
nowtime = 1
maxtime =1
f= open("input.txt","w")
#print(input1)
input1 = 50
ff = open("input1.txt","w")
ff.write(str(input1))
ff.write("\n")
#input1 = 50
add = 0
while(nowid<=input1):
    tpp = random.randint(1,50)
    if add < 3 and tpp > 40:
        f.write("[",)
        f.write("%.1f"%nowtime)
        f.write("]")
        if add == 0:
            f.write("X1")
        if add == 1:
            f.write("X2")
        if add == 2:
            f.write("X3")
        f.write("-ADD-ELEVATOR-")
        typee = random.randint(1,3)
        if typee == 1:
            f.write("A")
        if typee == 2:
            f.write("B")
        if typee == 3:
            f.write("C")
        f.write("\n")
        add = add + 1
    else:
        from1 = 15
        while from1 == 0:
            from1 = random.randint(-3,20)
        to = 3
        while to==from1 or to ==0 :
            to = random.randint(-3,20)
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
    a = random.uniform(0,min(maxtime-nowtime,10))
    b = round(a,1)
    #print(b)
    nowtime += b
f.close()
