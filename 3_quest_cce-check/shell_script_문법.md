


# 배열
1. 배열 선언
배열명 = () 

2. 배열 길이
 ${#ARRAY_NAME[*]}" 또는 ${#ARRAY_NAME[@]}"
 
예제입니다.

```shell
#!/bin/bash

FRUITS[0]="Apple"
FRUITS[1]="Banana"
FRUITS[2]="Kiwi"
FRUITS[3]="Grape"

echo "Array length: ${#FRUITS[*]}"
echo "Array length: ${#FRUITS[@]}"
```


# >와 »의 차이

'>': 명령어 뒤에 나오는 파일에 쓸 때 사용(=write or overwrite)
'>>': 명령어 뒤에 나오는 파일에 추가할 때 사용(=append)


