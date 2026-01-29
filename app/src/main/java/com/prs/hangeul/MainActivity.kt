package com.prs.hangeul

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private var permissionRequest: PermissionRequest? = null

    companion object {
        private const val AUDIO_PERMISSION_REQUEST_CODE = 1001
    }

    // 웹앱 URL
    private val DEV_URL = "https://www.icks-hangeul-test.prshub.net/"
    // private val DEV_URL = "http://10.0.2.2:5173"  // 에뮬레이터용
    // private val DEV_URL = "http://10.9.10.60:5173"  // 실제 기기용 (본인 PC IP로 변경)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        setupWebView()
        setupSwipeRefresh()

        // Vue.js 개발 서버 로드
        webView.loadUrl(DEV_URL)
    }

    private fun setupWebView() {
        webView.apply {
            settings.apply {
                // JavaScript 활성화 (필수3)
                javaScriptEnabled = true
                
                // DOM Storage 활성화 (Vue.js 필요)
                domStorageEnabled = true
                
                // 파일 접근 허용
                allowFileAccess = true
                allowContentAccess = true
                
                // 캐시 모드 설정 (개발 중에는 LOAD_NO_CACHE 권장)
                cacheMode = WebSettings.LOAD_NO_CACHE
                
                // 줌 기능 비활성화 (Canvas 터치 이벤트 충돌 방지)
                setSupportZoom(false)
                builtInZoomControls = false
                displayZoomControls = false
                
                // 태블릿 최적화
                useWideViewPort = true
                loadWithOverviewMode = true
                
                // Mixed Content 허용 (localhost HTTP 접근용)
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                
                // 데이터베이스 활성화
                databaseEnabled = true

                // 마이크/미디어 관련 설정 (getUserMedia 작동을 위해 필요)
                mediaPlaybackRequiresUserGesture = false
                
                // User Agent 설정 (필요시)
                userAgentString = "${userAgentString} VueTabletApp/1.0"
            }

            // WebViewClient 설정
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    // 페이지 로드 완료 - 새로고침 인디케이터 숨김
                    swipeRefreshLayout.isRefreshing = false
                }

                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    Toast.makeText(
                        this@MainActivity,
                        "페이지 로드 실패: ${error?.description}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Basic Auth 인증 처리
                override fun onReceivedHttpAuthRequest(
                    view: WebView?,
                    handler: HttpAuthHandler?,
                    host: String?,
                    realm: String?
                ) {
                    handler?.proceed("prsdev", "prsdev2025")
                }
            }

            // WebChromeClient 설정 (콘솔 로그, 알림 등)
            webChromeClient = object : WebChromeClient() {
                override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                    consoleMessage?.let {
                        android.util.Log.d(
                            "WebView Console",
                            "${it.message()} -- From line ${it.lineNumber()} of ${it.sourceId()}"
                        )
                    }
                    return true
                }

                override fun onJsAlert(
                    view: WebView?,
                    url: String?,
                    message: String?,
                    result: JsResult?
                ): Boolean {
                    // JavaScript alert 처리
                    return super.onJsAlert(view, url, message, result)
                }

                // WebView에서 마이크 권한 요청 처리
                override fun onPermissionRequest(request: PermissionRequest?) {
                    request?.let {
                        for (resource in it.resources) {
                            if (resource == PermissionRequest.RESOURCE_AUDIO_CAPTURE) {
                                // 앱에 마이크 권한이 있는지 확인
                                if (ContextCompat.checkSelfPermission(
                                        this@MainActivity,
                                        Manifest.permission.RECORD_AUDIO
                                    ) == PackageManager.PERMISSION_GRANTED
                                ) {
                                    // 권한이 있으면 WebView 요청 허용
                                    it.grant(arrayOf(PermissionRequest.RESOURCE_AUDIO_CAPTURE))
                                } else {
                                    // 권한이 없으면 요청 저장 후 런타임 권한 요청
                                    permissionRequest = it
                                    ActivityCompat.requestPermissions(
                                        this@MainActivity,
                                        arrayOf(Manifest.permission.RECORD_AUDIO),
                                        AUDIO_PERMISSION_REQUEST_CODE
                                    )
                                }
                                return
                            }
                        }
                        // 다른 권한 요청은 거부
                        it.deny()
                    }
                }

                override fun onPermissionRequestCanceled(request: PermissionRequest?) {
                    super.onPermissionRequestCanceled(request)
                    permissionRequest = null
                }
            }

            // JavaScript Interface 추가 (앱 ↔ 웹 통신)
            addJavascriptInterface(WebAppInterface(this@MainActivity), "Android")

            // Canvas 터치 이벤트를 위한 설정
            setOnTouchListener { v, event ->
                // 터치 이벤트가 WebView 내부(Canvas)로 전달되도록 함
                when (event.action) {
                    MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                        // 부모 뷰가 터치 이벤트를 가로채지 않도록 함
                        v.parent.requestDisallowInterceptTouchEvent(true)
                    }
                    MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                        v.parent.requestDisallowInterceptTouchEvent(false)
                    }
                }
                // false를 반환하여 WebView가 이벤트를 계속 처리하도록 함
                false
            }

            // 오버스크롤 비활성화
            overScrollMode = View.OVER_SCROLL_NEVER
        }
    }

    private fun setupSwipeRefresh() {
        swipeRefreshLayout.apply {
            // 새로고침 시 WebView 리로드
            setOnRefreshListener {
                webView.reload()
            }

            // 새로고침 인디케이터 색상 설정
            setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
            )
        }
    }

    // 런타임 권한 요청 결과 처리
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == AUDIO_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용되면 WebView 요청 허용
                permissionRequest?.grant(arrayOf(PermissionRequest.RESOURCE_AUDIO_CAPTURE))
            } else {
                // 권한이 거부되면 WebView 요청 거부
                permissionRequest?.deny()
                Toast.makeText(this, "마이크 권한이 필요합니다", Toast.LENGTH_SHORT).show()
            }
            permissionRequest = null
        }
    }

    // 뒤로가기 버튼 처리
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    // 앱 일시정지/재시작 시 WebView 처리
    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}
