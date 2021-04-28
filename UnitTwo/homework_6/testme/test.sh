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
 cat input.txt | ./getpy2.exe > check.py
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

