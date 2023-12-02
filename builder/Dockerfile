FROM adoptopenjdk/openjdk11
WORKDIR /app
RUN apt-get update \
    && apt-get install -y git

RUN git clone https://github.com/SexualWhiteChocolate/ISD_BIT_Lab3.git \
    && mv ISD_BIT_Lab3/Consumer /app/Consumer \
    && mv ISD_BIT_Lab3/Producer /app/Producer \
    && rm -rf ISD_BIT_Lab3

WORKDIR /app/Consumer
RUN javac Main.java \
    && jar cfe Consumer.jar Main.class MiniClass.class ru.sber.karimullin.hw_3.Factory.Factory.class \
     ru.sber.karimullin.hw_3.Generator.Generator.class \
    ru.sber.karimullin.hw_3.RuntimeCompiler.RuntimeCompiler.class \
    && rmdir -rf ru \
    && rm -rf Main.* MiniClass.*

WORKDIR /app/Producer
RUN javac Main.java \
    && jar cfe Producer.jar Main.class MiniClass.class \
    && rm -rf Main.* MiniClass.*

