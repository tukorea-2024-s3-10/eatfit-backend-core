name: Feature
description: 새로운 기능 추가
title: "[feat] "
labels: [feature]
assignees: []

body:
  - type: input
    id: environment
    attributes:
      label: "환경 정보"
      description: "OS, 브라우저 등 환경 정보를 입력해주세요."
      placeholder: "예) Windows 10, Chrome 120.0"

  - type: textarea
    id: reproduction
    attributes:
      label: "재현 방법"
      description: "버그가 발생하는 단계를 설명해주세요."
      placeholder: "1. ~~을 클릭한다\n2. ~~을 입력한다\n3. ~~ 오류 발생"

  - type: checkboxes
    id: confirmation
    attributes:
      label: "확인 사항"
      description: "아래 확인 사항을 체크해주세요."
      options:
        - label: "이미 존재하는 이슈를 검색해봤습니다."
          required: true
        - label: "최신 버전에서 동일한 문제가 발생하는지 확인했습니다."
          required: false
