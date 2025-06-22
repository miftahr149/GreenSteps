@echo off

DEL /s /q bin\*

FOR /f "tokens=*" %%d IN (compileFolder.txt) DO (
  javac -d bin -cp bin %%d/*.java
)

cls