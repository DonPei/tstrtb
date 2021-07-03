Set oShell = CreateObject ("Wscript.Shell") 
Dim strArgs
strArgs = "cmd /c rtb.bat"
oShell.Run strArgs, 0, false
