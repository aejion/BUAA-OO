cat input.txt | ./getpy.exe > check.py
        python check.py
        sleep 20
        cat input1.txt > all.txt
        cat input.txt >> all.txt
        cat output.txt >>all.txt
        cat all.txt | java -jar test.jar > answer.txt

