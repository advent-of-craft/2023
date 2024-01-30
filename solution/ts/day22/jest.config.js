module.exports = {
    preset: "ts-jest",
    testEnvironment: "node",
    transform: {'^.+\\.ts?$': 'ts-jest'},
    testRegex: '/tests/.*\\.(test|spec)?\\.(ts|tsx)$'
};