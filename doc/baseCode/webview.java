webview = (WebView) findViewById(R.id.webview);
        webview.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                Intent intent = new Intent(WebviewActivity.this, ResultActivity.class);
                intent.putExtra("type", type);
                if (type == 0)
                    intent.putExtra("bizid", mBiz_id);
                else
                    intent.putExtra("token", token);

                startActivity(intent);
                return false;
            }
        });
        WebSettings webSettings = webview.getSettings();
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webview.setScrollbarFadingEnabled(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setAllowFileAccess(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setSupportZoom(true);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB)
        {
            // Hide the zoom controls for HONEYCOMB+
            webSettings.setDisplayZoomControls(false);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webview.loadUrl(url);
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {

                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                super.onPageFinished(view, url);

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url)
            {
                super.onLoadResource(view, url);
            }


        });
        webview.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                Log.d("MainActivity", "onPermissionRequest");
                WebviewActivity.this.runOnUiThread(new Runnable() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void run() {
                        request.grant(request.getResources());
                    }
                });
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);
            }

            // Work on Android 4.4.2 Zenfone 5
            public void showFileChooser(ValueCallback<String[]> filePathCallback, String acceptType, boolean paramBoolean)
            {


                // TODO Auto-generated method stub
            }

            //for  Android 4.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture)
            {

                if (nFilePathCallback != null)
                {
                    nFilePathCallback.onReceiveValue(null);
                }
                nFilePathCallback = uploadMsg;
                if ("image/*".equals(acceptType))
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                    {
                        File photoFile = null;
                        try
                        {
                            photoFile = createImageFile();
                            takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                        }
                        catch (IOException ex)
                        {
                            Log.e("TAG", "Unable to create Image File", ex);
                        }
                        if (photoFile != null)
                        {
                            mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        }
                        else
                        {
                            takePictureIntent = null;
                        }
                    }
                    startActivityForResult(takePictureIntent, INPUT_FILE_REQUEST_CODE);
                }
                else if ("video/*".equals(acceptType))
                {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null)
                    {
                        startActivityForResult(takeVideoIntent, INPUT_VIDEO_CODE);
                    }
                }
            }

            @SuppressLint("NewApi")
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams)
            {
                if (mFilePathCallback != null)
                {
                    mFilePathCallback.onReceiveValue(null);
                }
                mFilePathCallback = filePathCallback;
                String[] acceptTypes = fileChooserParams.getAcceptTypes();
                if (acceptTypes[0].equals("image/*"))
                {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                    {
                        File photoFile = null;
                        try
                        {
                            photoFile = createImageFile();
                            takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                        }
                        catch (IOException ex)
                        {
                            Log.e("TAG", "Unable to create Image File", ex);
                        }
                        //适配7.0
                        if (Build.VERSION.SDK_INT > M)
                        {
                            if (photoFile != null)
                            {
                                photoURI = FileProvider.getUriForFile(WebviewActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
                                takePictureIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            }
                        }
                        else
                        {
                            if (photoFile != null)
                            {
                                mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            }
                            else
                            {
                                takePictureIntent = null;
                            }
                        }
                    }
                    startActivityForResult(takePictureIntent, INPUT_FILE_REQUEST_CODE);
                }
                else if (acceptTypes[0].equals("video/*"))
                {
                    Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    if (takeVideoIntent.resolveActivity(getPackageManager()) != null)
                    {
                        startActivityForResult(takeVideoIntent, INPUT_VIDEO_CODE);
                    }
                }
                return true;
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage)
            {
                return true;
            }
        });

        webview.loadUrl(url);