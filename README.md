# YouTube AutoSkip — 

Personal-use Android Accessibility Service.

## What it does

When the official YouTube Android app exposes a clickable **Skip Ad** control in its accessibility/UI tree, this service attempts to click it immediately.

It intentionally does NOT:
- use the Internet
- run a server
- collect analytics
- keep counters
- use a database
- take screenshots
- run a continuous polling loop
- interact with other apps

## Poco M2

The project targets Android API 26+ and is suitable for a Poco M2 running Android 11. The service is not tied to a particular YouTube APK version because it identifies the Skip Ad control from YouTube's accessibility tree rather than depending on YouTube's internal code.

Important: no source-only project can guarantee compatibility with every future YouTube UI. YouTube can change its accessibility labels/tree at any time.

## Build on a phone with GitHub Actions

This repository includes a workflow at `.github/workflows/build-apk.yml`.

1. Create a GitHub repository.
2. Upload this whole project.
3. Open **Actions**.
4. Run **Build APK**.
5. Download the generated `YouTubeAutoSkip-debug-apk` artifact.
6. Extract the APK and install it on the Poco M2.
7. Open the app.
8. Android Settings -> Accessibility -> YouTube AutoSkip -> ON.
9. Open YouTube.

You may need to allow installation of APKs from the browser/file manager used to download the artifact.

## Important

This is a personal sideloaded app, not a Play Store package. Android's Accessibility Service is a privileged user-enabled feature; the user must explicitly enable it.

The app has no INTERNET permission.
