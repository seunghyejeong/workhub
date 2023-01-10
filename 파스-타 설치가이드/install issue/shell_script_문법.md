


# 배열

2. 배열 길이
배열 길이는 ${#ARRAY_NAME[*]}" 또는 ${#ARRAY_NAME[@]}"처럼, 배열의 모든 요소를 의미하는 ARRAY_NAME[@] 앞에 #을 입력하시면 됩니다.

다음은 배열 길이를 출력하는 예제입니다.

```shell
#!/bin/bash

FRUITS[0]="Apple"
FRUITS[1]="Banana"
FRUITS[2]="Kiwi"
FRUITS[3]="Grape"

echo "Array length: ${#FRUITS[*]}"
echo "Array length: ${#FRUITS[@]}"
```

# 배열이 통으로 들어갔구나~


```shell

#!/bin/bash

COPY_DIR=/home/ubuntu/workspace/copy
CCE_DIR=/home/ubuntu/workspace/origin

mkdir -p ${COPY_DIR}
cp -r ${CCE_DIR}/* ${COPY_DIR}


declare -a files
cd ${COPY_DIR}
files=`ls ${COPY_DIR} | find -maxdepth 1 -name "*.txt"`         #txt파일을 모두 배열에 담음 
echo ${files}

length=${#files[@]}
echo ${length}

for (( i=0; i <= ${length}; i++))
do
        echo $i ${files[$i]}
done
```

결과

```consolel
ubuntu@paasta-ta-bami-inception-1:~/workspace$ source test.sh 
./test1.txt ./test2.txt ./test3.txt
1
0 ./test1.txt ./test2.txt ./test3.txt
1
```


# 경로를 배열에 담는것은 성공..

```shell
#!/bin/bash
  
COPY_DIR=/home/ubuntu/workspace/copy
CCE_DIR=/home/ubuntu/workspace/origin

mkdir -p ${COPY_DIR}
cp -r ${CCE_DIR}/* ${COPY_DIR}


cd ${COPY_DIR}
files=(`ls ${COPY_DIR} | find -maxdepth 1 -name "*.txt"`)
echo ${files[*]}




for file in "${files[@]}"
do
        echo $i ${file[$i]}
done
```

결과

```console
ubuntu@paasta-ta-bami-inception-1:~/workspace$ source test.sh 
./test1.txt ./test2.txt ./test3.txt
./test1.txt
./test2.txt
./test3.txt
```

# cce-check-to-inception.sh 에 반복 돌며 text file 입력 된거 확인

```shell
#!/bin/bash
  
COPY_DIR=/home/ubuntu/workspace/copy
CCE_DIR=/home/ubuntu/workspace/origin
TMP_DIR=/tmp



mkdir -p ${COPY_DIR}
cp -r ${CCE_DIR}/* ${COPY_DIR}


cd ${COPY_DIR}
files=(`ls ${COPY_DIR} | find -maxdepth 1 -name "*.txt"`)


for file in "${files[@]}"
do
        echo ${file[$i]} >> ${COPY_DIR}/cce-check-to-inception.sh
done
```

cce-check-to-inception.sh

```shell
./test1.txt
./test2.txt
./test3.txt
~                                                                                                                                                                      
~            
```
# 이젠 뭘 echo 하고 >> cce-check-to-inception.sh로 가야하지? 
# 근데 naming 별루.............. cce-result-to inception..? throw ince?

