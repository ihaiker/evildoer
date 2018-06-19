build:
	gradle build -x test

ref-def:
	gradle build -x test --refresh-dependencies  --recompile-scripts

clean:
	gradle clean