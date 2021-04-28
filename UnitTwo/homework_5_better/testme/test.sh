while true
do
	python datamake.py
	echo ' ' > check.py
	./datacheck_win -i input.txt > std.txt
	cat input.txt | ./getpy.exe >> check.py
	python check.py
	sleep 20
	cat std.txt > all.txt
	cat output.txt >> all.txt
	cat all.txt | ./getans.exe > answer.txt
	cat all.txt | ./getans2.exe >> out1.txt
	file1=answer.txt
	file2=ac.txt
	diff $file1 $file2 > /dev/null
	if [ $? != 0 ]
	then
		break
	fi
	echo ' ' > check.py
	cat input.txt | ./getpy2.exe >> check.py
        python check.py
        sleep 20
        cat std.txt > all.txt
        cat output.txt >> all.txt
        cat all.txt | ./getans.exe > answer.txt
	cat all.txt | ./getans2.exe >> out2.txt
	file1=answer.txt
        file2=ac.txt
        diff $file1 $file2 > /dev/null
        if [ $? != 0 ]
        then
                break
        fi
done
