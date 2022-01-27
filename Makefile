clean:
	./gradlew clean

assemble:
	./gradlew assembleDebug

install:
	./gradlew installDebug

test:
	./gradlew testDebug

lint:
	./gradlew ktlintCheck