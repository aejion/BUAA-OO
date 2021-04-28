 
import subprocess
import time
import threading
ff = open("output.txt","w")
output1 = subprocess.Popen("java -jar G:\compus\OO\homework_5_better\homework_5_better.jar",stdin = subprocess.PIPE,stdout = ff,shell=True)
output1.stdin.write(b"1-FROM-12-TO-5\n")
output1.stdin.flush()
time.sleep(1.9)
output1.stdin.write(b"2-FROM-3-TO-7\n")
output1.stdin.flush()
time.sleep(3.0)
output1.stdin.write(b"3-FROM-3-TO-11\n")
output1.stdin.flush()
time.sleep(0.4)
output1.stdin.write(b"4-FROM-5-TO-1\n")
output1.stdin.flush()
output1.stdin.close()
ff.close()
