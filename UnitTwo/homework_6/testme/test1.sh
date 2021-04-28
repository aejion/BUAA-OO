while true
do
        cat input.txt | ./getpy.exe > check.py
        python check.py
	sleep 50
	cat input.txt > all.txt
	cat output.txt >>all.txt
	cat all.txt | java -jar test.jar >answer.txt
	sleep 5
	echo "1:" >>time.txt
	cat all.txt | ./getans.exe >> time.txt
	file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy2.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
	echo "2:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy3.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
        echo "3:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy4.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
        echo "4:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy5.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
        echo "5:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy6.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
        echo "6:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy7.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
        echo "7:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
	cat input.txt | ./getpy8.exe > check.py
        python check.py
        sleep 50
        cat input.txt > all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar >answer.txt
        sleep 5
        echo "8:" >> time.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
done
