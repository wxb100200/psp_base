rmdir /S /Q ..\v2\resources
del /F /S /Q ..\v2\app.js
del /F /S /Q ..\v2\index.html
sencha app build
xcopy /Y /E build\production\PSP ..\v2
