build:
	gradle build -x test

ref-def:
	gradle build -x test --refresh-dependencies  --recompile-scripts --scan

in:
	gradle install -x test

up:
	gradle clean upload -x test

clean:
	gradle clean