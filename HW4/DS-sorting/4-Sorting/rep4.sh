#!/usr/bin bash
echo "-compile start-"

# Compile
javac SortingTestRep.java

mkdir -p ./reptest4/rep_output


echo "-execute your program-"
for i in $(seq 1 60)
do
        # 무한루프를 방지하기 위해 input 당 시간제한 1초
	timeout 1000000 java SortingTestRep < ./reptest4/input/$i.txt > ./reptest4/rep_output/$i.txt
done
