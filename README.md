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

## Device Compatibility

The project targets Android API 26+ (Android 8.0 and above), so it works on any Android phone in that range — for example a Poco M2 running Android 11 is one such device, not the only one. The service is not tied to a particular YouTube APK version either, because it identifies the Skip Ad control from YouTube's accessibility tree rather than depending on YouTube's internal code.

Important: no source-only project can guarantee compatibility with every future YouTube UI. YouTube can change its accessibility labels/tree at any time.

## How to Build & Install (via GitHub Actions — no PC needed)

This repo includes a ready-made workflow at `.github/workflows/build-apk.yml` that compiles the APK in the cloud. No Android Studio and no computer required — this can be done entirely from a phone browser.

### 1. Fork this repository
- Open this repo on GitHub and tap **Fork** (top-right) to create your own copy under your account.
- Forks have Actions disabled by default. Go to the **Actions** tab of your fork and click **"I understand my workflows, go ahead and enable them"**.

### 2. Run the build workflow
- In your fork's **Actions** tab, select **Build APK** from the workflow list on the left.
- Click **Run workflow → Run workflow** (green button). This triggers it manually — it also runs automatically on any push to `main`/`master`.
- Wait 2–4 minutes for the run to finish (green checkmark = success).

### 3. Download the APK
- Open the completed workflow run.
- Scroll to the **Artifacts** section at the bottom and download **YouTubeAutoSkip-debug-apk**.
- This downloads as a `.zip` — extract it to get `app-debug.apk`.

### 4. Install on your phone
- Open `app-debug.apk` on your Android phone (Android 8.0 / API 26 or newer).
- If prompted, allow installs from your browser or file manager ("Install unknown apps") for this one install, then confirm.

### 5. Enable the Accessibility permission
- Open the **YouTube AutoSkip** app you just installed.
- Tap **"Open Accessibility Settings"** inside the app (or go manually: **Settings → Accessibility → Installed/Downloaded apps → YouTube AutoSkip**).
- Turn it **ON** and confirm the permission dialog.

### 6. Test it
- Open the YouTube app and play a video with a skippable ad. The service should tap **Skip Ad** as soon as it becomes clickable.

To stop it at any time, just turn the toggle off in Accessibility settings — that fully disables the service (no need to uninstall).

## Troubleshooting

### "Harmful app blocked" / install warning from Google Play Protect

This happens because Play Protect treats **sideloaded + Accessibility permission** as a high-risk combination (it's a common pattern in banking-trojan malware, so Google blocks it by default for apps installed outside the Play Store).

- If a warning appears with a **"More details"** link, tap it, then tap **Install anyway**.
- If there's no such option (a hard block): open the **Play Store app → profile icon (top-right) → Play Protect → gear icon (Settings) → turn OFF "Scan apps with Play Protect"**, then install the APK. You can turn scanning back on afterward.

### Accessibility toggle is greyed out / "For your security, this setting is currently unavailable"

This is a separate Android 13+ restriction (not Play Protect) that blocks turning on an Accessibility Service for freshly sideloaded apps.

- Go to **Settings → Apps → All apps → YouTube AutoSkip**.
- Tap the **3-dot menu (top-right) → "Allow restricted settings"** and confirm.
- Go back into **Accessibility settings** — the toggle can now be turned ON.

Both protections exist because malware frequently abuses the Accessibility permission (e.g. to read OTPs or overlay banking apps). Only bypass them for a build you compiled yourself or otherwise fully trust — never for a random APK from someone else.

## Important

This is a personal sideloaded app, not a Play Store package. Android's Accessibility Service is a privileged user-enabled feature; the user must explicitly enable it.

The app has no INTERNET permission.
