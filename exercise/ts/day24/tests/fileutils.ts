import * as fs from 'fs';
import * as path from 'path';

const getInputAsString = (inputPath: string): string => {
    const fullPath = path.join(__dirname, inputPath);
    return fs.readFileSync(fullPath, 'utf8');
};

export const getInputAsSeparatedLines = (inputPath: string): string[] =>
    getInputAsString(inputPath).split(/\r?\n/);