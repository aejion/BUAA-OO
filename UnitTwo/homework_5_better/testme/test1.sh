 	
	echo ' ' > check.py
        cat input.txt | ./getpy2.exe >> check.py
	./datacheck_win -i input.txt > std.txt
        python check.py
        sleep 20
        cat std.txt > all.txt
        cat output.txt >> all.txt
        cat all.txt | ./getans.exe > answer.txt

