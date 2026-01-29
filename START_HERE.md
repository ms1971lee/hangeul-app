# 🚀 시작하기

Vue.js WebView Android 앱 프로젝트에 오신 것을 환영합니다!

## ⚡ 3분 안에 시작하기

### 1️⃣ Android Studio에서 프로젝트 열기
```
Android Studio → File → Open → VueTabletApp 폴더 선택
```

### 2️⃣ Firebase 설정 (필수!)
1. https://console.firebase.google.com 접속
2. 프로젝트 추가
3. Android 앱 등록 (패키지: `com.yourcompany.vueapp`)
4. **google-services.json 다운로드**
5. `app/` 폴더에 복사

### 3️⃣ IP 주소 설정
`MainActivity.kt` 24번째 줄:
```kotlin
private val DEV_URL = "http://192.168.0.10:5173"  // ← 본인 IP로 변경
```

### 4️⃣ Vue 서버 실행
```bash
npm run dev -- --host
```

### 5️⃣ 앱 실행
```
Android Studio → ▶ Run
```

## 📚 상세 가이드

- **SETUP_GUIDE.md** ← 전체 설정 과정 (초보자용)
- **README.md** ← API 사용법 및 기능 설명

## ⚠️ 중요!

**google-services.json 없으면 빌드 실패합니다!**

Firebase Console에서 반드시 다운로드하세요:
1. https://console.firebase.google.com
2. 프로젝트 추가 → Android 앱 등록
3. google-services.json 다운로드
4. `VueTabletApp/app/` 폴더에 복사

## 🎯 프로젝트 구조

```
VueTabletApp/
├── app/
│   ├── google-services.json         ⚠️ Firebase에서 다운로드 필요!
│   ├── build.gradle
│   └── src/main/
│       ├── java/com/yourcompany/vueapp/
│       │   ├── MainActivity.kt       ← IP 주소 수정
│       │   ├── WebAppInterface.kt
│       │   └── ...
│       ├── res/
│       └── AndroidManifest.xml
├── build.gradle
├── settings.gradle
├── README.md                         ← API 사용법
└── SETUP_GUIDE.md                    ← 상세 설정 가이드
```

## 💡 Vue에서 Android 기능 사용

```javascript
// Toast 메시지
window.Android.showToast("Hello!");

// 디바이스 정보
const info = JSON.parse(window.Android.getDeviceInfo());

// FCM 토큰
const token = window.Android.getFCMToken();
```

## 🆘 문제 해결

### localhost 연결 안 됨?
- PC와 기기가 같은 Wi-Fi인지 확인
- Vue 서버가 `--host` 옵션으로 실행되었는지 확인
- 방화벽에서 포트 5173 허용

### 빌드 오류?
```
Build → Clean Project
Build → Rebuild Project
```

### google-services.json 오류?
- 파일이 `app/` 폴더에 있는지 확인
- 패키지 이름이 `com.yourcompany.vueapp`인지 확인

## 🎉 준비 완료!

이제 Vue.js로 화면을 만들고 Android 네이티브 기능을 사용할 수 있습니다!

**다음 단계:**
1. SETUP_GUIDE.md 읽기 (처음 사용자)
2. README.md 읽기 (API 사용법)
3. 앱 실행 및 테스트

궁금한 점이 있으면 README.md의 "문제 해결" 섹션을 확인하세요!
