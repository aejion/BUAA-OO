while true
do
 	python datamake.py
        cat input.txt | ./getpy.exe > check.py
        python check.py
	sleep 80
	cat input1.txt > all.txt
	cat input.txt >> all.txt
        cat output.txt >>all.txt
	cat all.txt | java -jar test.jar > answer.txt
        cat all.txt | ./getans.exe >> time.txt
        file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi	
done
