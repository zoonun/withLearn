# frontend

## Project tree

### assets
- 이미지, 폰트 등 자원 파일

### views
- 각 페이지 별로 나누어진 vue와 components 폴더

### store
- Vuex state 관리

### router
- Vue router 관리

## Vuex State Convention
- 모든 함수는 camelCase로 작성한다.

### Actions
- request로 시작한다. ex) requestLogin
- axios 통신, localStorage 저장 등의 실제 동작을 수행한다.
- commit으로 mutations의 함수를 호출해 state 값을 변경한다.

### Mutations
- set으로 시작한다. ex) setPlatform
- state 값의 변화만 담당해야 한다.

### Getters

- get으로 시작한다. ex) getActiveMenuIndex
- state의 값을 리턴한다.
