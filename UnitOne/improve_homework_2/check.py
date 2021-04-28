#!/usr/bin/python
dataset1=[]
dataset2=[]
with open('outputcorrect.txt') as file1:
    for content in file1:
        dataset1.append(content.rstrip())
with open('outputunknown.txt') as file2:
    for content in file2:
        dataset2.append(content.rstrip())
len1=len(dataset1)
len2=len(dataset2)
fo = open("answer.txt","w")
flag = 1
if len1 != len2:
    print("Your Output is not enough")
else:
    for i in range(0,len1-1):
        temp1 = float(dataset1[i])
        temp2 = float(dataset2[i])
        eps = abs(temp1-temp2)/max(abs(temp1),1)
        if eps > 10e-8:
            flag = 0
            fo.write("We expect ")
            fo.write(str(temp1))
            fo.write(",but got ")
            fo.write(str(temp2))
            fo.write(" in line")
            fo.write(str(i+1))
            fo.write("\n")
if flag == 1:
    fo.write("Accept")
fo.close()
file1.close()
file2.close()
