FROM mcr.microsoft.com/devcontainers/java:17-bullseye as base

ARG USERNAME=vscode

# install curl and clean up to minimize image size
RUN apt-get update && \
    apt-get install -y netcat libicu-dev git curl wget && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

ENV HOME=/home/$USERNAME    

FROM base as add_maven

USER root

ENV MAVEN_VERSION=3.9.11
# Install Maven
RUN wget https://dlcdn.apache.org/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzvf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /usr/share && \
    mv /usr/share/apache-maven-${MAVEN_VERSION} /usr/local/maven && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz 

ENV PATH=$PATH:/usr/local/maven/bin
ENV MAVEN_HOME=/usr/local/maven

FROM add_maven as add_nvm

USER vscode

WORKDIR $HOME

# Create a script file sourced by both interactive and non-interactive bash shells
ENV BASH_ENV $HOME/.bash_env
RUN touch "${BASH_ENV}"
RUN echo '. "${BASH_ENV}"' >> $HOME/.bashrc

ENV NODE_VERSION=20
ENV NVM_DIR=$HOME/.nvm

RUN mkdir -p $NVM_DIR
RUN curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.1/install.sh | PROFILE="${BASH_ENV}" bash \
    && echo node > .nvmrc \
    && . $NVM_DIR/nvm.sh \
    && nvm install $NODE_VERSION \
    && nvm alias default $NODE_VERSION \
    && nvm use default \
    && npm install -g npm \
    && npm install -g yarn \
    && npm install -g firebase-tools

FROM add_nvm as runner
USER vscode
WORKDIR $HOME