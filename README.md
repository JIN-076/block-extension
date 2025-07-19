# block-extension
blocking file-extension service: 파일 확장자 차단 서비스

## 빌드 및 실행 방법
1. Repository Clone

```shell
  git clone https://github.com/JIN-076/block-extension
```

2. Build Application

```shell
  cd block-extension/
  docker-compose up -d
```

3. 브라우저에서 서비스 접속

```shell
  http://localhost:8080
```

4. 배포되어 있는 서비스 접속

```shell
  http://<ip-address>:8080
```

<Br>

## REST API 명세 요약

### 1. 차단할 커스텀 확장자 추가 API

#### Request
- Method: `POST`
- URL: `/api/v1/blocked-extension/custom`
- RequestBody: 
```json
{
  "extensionName": "sh"
}
```

```shell
   POST http://localhost:8080/api/v1/blocked-extension/custom
```

#### Response
ExtensionIdResponse.class

- Status: `201 Created`
- Body:
```json
{
  "id": 8,
  "name": "sh"
}
```

<Br>

### 2. 고정 확장자 활성화/비활성화 API

#### Request
- Method: `PATCH`
- URL: `/api/v1/blocked-extension/fixed/{extensionName}`
- RequestBody:
```json
{
  "isEnabled": true
}
```

> `isEnabled=true` => '해당 고정 확장자를 활성화할거야!' 라는 의미

```shell
  PATCH http://localhost:8080/api/v1/blocked-extension/fixed/exe
```

#### Response
FixedExtensionResponse.class

- Status: `200 OK`
- Body:
```json
{
  "id": 1,
  "name": "bat",
  "isEnabled": true
}
```

<Br>

### 3. 고정 확장자 리스트 조회 API

#### Request
- Method: `GET`
- URL: `/api/v1/blocked-extension/fixed`

```shell
  GET http://localhost:8080/api/v1/blocked-extension/fixed
```

#### Response
FixedExtensionInfoResponse.class

- Status: `200 OK`
- Body:

```json
{
  "extensions": [
    {
      "id": 1,
      "name": "bat",
      "isEnabled": true
    },
    {
      "id": 2,
      "name": "cmd",
      "isEnabled": false
    }
  ]
}
```

<Br>

### 4. 커스텀 확장자 리스트 조회 API

#### Request
- Method: `GET`
- URL: `/api/v1/blocked-extension/custom`

```shell
  GET http://localhost:8080/api/v1/blocked-extension/custom
```

#### Response
CustomExtensionInfoResponse.class

- Status: `200 OK`
- Body:

```json
{
  "extensions": [
    {
      "id": 8,
      "name": "sh"
    },
    {
      "id": 9,
      "name": "java"
    }
  ]
}
```

<Br>

### 5. 커스텀 확장자 삭제 API

#### Request
- Method: `DELETE`
- URL: `/api/v1/blocked-extension/custom/{extensionName}`

```shell
  DELETE http://localhost:8080/api/v1/blocked-extension/custom/sh
```

#### Response
Void

- Status: `204 No Content`

<Br>

## 설계 고려 요소

### 파일 확장자 차단 과제 요구사항 정리

1. 고정 확장자는 차단을 자주 하는 확장자 리스트, default: uncheck (비활성화 상태)
2. 고정 확장자를 check/uncheck 시, DB에 저장되며, 새로고침 시에 유지되어야 함
3. 확장자 최대 입력 길이는 20자리
4. `추가` 버튼 클릭 시, 차단하려는 커스텀 확장자가 DB에 저장되며 아래쪽 영역에 표현
5. 커스텀 확장자는 최대 200개까지 추가 가능
6. 확장자 옆 `x` 클릭 시, DB에서 삭제

### 추가 고려 요소

#### 1. 고정 확장자의 활성화/비활성화 API 설계 접근 방식

고정 확장자는 초기에 `unchecked` 상태로 조회가 되어야 하며, check/uncheck를 통해 DB에 저장이 되어야 합니다.
고정 확장자 목록은 체크 여부와 상관없이 계속 렌더링되어야 하기 때문에, 초기 데이터를 `unchecked` 상태로 넣어주었습니다.
이후, `check` 선택을 하면 차단이 활성화되어 내부 필드 `isEnabled` 가 `true` 로, `uncheck` 선택을 하면 차단이 비활성화되어 
내부 필드 `isEnabled` 가 `false` 로 변하도록 설계했습니다. 그리고 이를 클라이언트에게 응답하는 과정에서 `활성화 여부` 를 같이 내려줌으로써
현재 선택되었는지, 안되었는지를 렌더링할 수 있도록 구현했습니다.

<br>

#### 2. 확장자 테이블 설계

고정 확장자든, 커스텀 확장자든 확장자를 차단하는 서비스가 올바르게 동작하기 위해서는 확장자 자체가 이를 식별할 수 있는 고유한 값이어야 합니다.
테이블을 설계하는 과정에서 `varchar(20)` 타입의 확장자를 PK로 선택하는 방식과 `auto_increment` 를 활용한 bigint 타입의 id를 PK로 선택하는
방식을 고민했습니다. 결과적으로 `auto_increment` 방식을 선택했으며, 그 이유로는 다음과 같습니다.

숫자 PK 방식은 고정된 8바이트 공간을 필요로 하며, 정수를 비교하기 때문에 인덱스와 조인 시 효율이 높으나,
문자열 PK 방식은 최대 20바이트의 공간을 필요로 하며 문자열을 비교하는 비용이 인덱스나 조인을 사용하는 비용을 크게 높이고
성능을 저하시킬 수 있다고 판단했습니다. 

이러한 판단을 바탕으로, 확장자 이름 필드에는 `unique` 제약조건을 추가로 걸어, 동일한 확장자가 등록되는 일을 방지했습니다.

<br>

#### 3. 확장자를 인자로 전달하는 방식

고정 확장자에 대해 활성화/비활성화하는 API, 커스텀 확장자를 삭제하는 API는 어떤 확장자에 대해 작업을 수행할지를 클라이언트로부터 받아와야 합니다.
이 때, @PathVariable로 순차적으로 증가하는 비교적 무의미한 `id` 값을 넘길지, 유의미하면서 식별자이기도 한 'name' 값을 넘길지 고민했습니다.
결과적으로, `extensionName` 을 넘기는 방식을 채택했고, 그 이유로는 다음과 같습니다.

- `extensionName` 필드가 도메인 자체의 자연 키이자 유니크 키이고, 한 번 등록된 값은 삭제가 될 지언정 수정은 절대 일어나지 않는다고 판단
- 어떤 확장자에 대해 작업을 수행하겠다는 직관적인 의미를 엔드포인트만으로 잘 드러낼 수 있음

<br>

#### 4. 커스텀 확장자를 등록하는 과정에서 유효성을 검증하는 방식

커스텀 확장자를 추가하는 과정에서는 크게 3가지의 유효성을 통과해야 합니다.
먼저, 고정 확장자를 포함하여 이미 차단 리스트에 포함이 되어 있지 않아야 합니다.
다음으로, 커스텀 확장자는 최대 20자를 넘을 수 없습니다.
마지막으로, 커스텀 확장자는 최대 200개까지 등록할 수 있습니다.

각각의 유효성을 검증하는 방식으로, 단순히 `Service` 로직에서 DB로부터 값이 존재하는지, 20자가 넘는지, 200개 미만인지를 판별하는 방식보다
Spring Validation에서 지원하는 @Size 어노테이션과 Jpa/Hibernate에서 지원하는 unique, updatable 등의 제약조건을 활용하여 유효성을 검증할 수 있도록 했습니다.
커스텀 확장자의 최대 저장 가능 수는 레코드 수를 `COUNT(*)` 로 가져와서, 현재 등록이 가능한 상황인지를 검증하도록 구현했습니다.

<Br>

#### 5. 서버 동기화

창을 여러 개 띄워놓고 A 페이지에서 고정 확장자에 대해 활성화/비활성화 작업을 수행했다고 가정하였을 때, B 페이지에서는 A 페이지에서 변경한 작업에 대해
새로고침을 하지 않았기 때문에 변경 사항이 화면 렌더링에 반영이 되지 않습니다. 이때, B 페이지에서 활성화 작업을 수행했을 때, 해당 변경사항만을 렌더링하고 
사용자에게 새로고침을 유도할 것인지, 서버 데이터 상태를 바탕으로 동기화하여 변경 작업을 한 번에 보여줄지에 대해 고민했습니다. 결과적으로, 사용자가 
새로고침을 하면 변경 사항이 바로 렌더링이 될 것이기 떄문에 굳이 새로고침을 하지 않더라도 자동으로 서버와 동기화되는 화면을 렌더링해주는 것이 사용자 입장에서 
혼란을 줄일 수 있는 방법이라고 판단하였습니다.