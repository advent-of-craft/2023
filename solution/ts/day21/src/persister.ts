import {promises as fsPromises} from "fs";
import {join} from "path";
import {FileContent, FileUpdate} from "./audit";

export class Persister {
    public readDirectory = async (directory: string): Promise<FileContent[]> => Promise.all(
        (await fsPromises.readdir(directory, {withFileTypes: true}))
            .filter(dirent => dirent.isFile())
            .map(dirent => this.readFile(join(directory, dirent.name)))
    );

    public applyUpdate = async (directory: string, update: FileUpdate): Promise<void> =>
        await fsPromises.writeFile(join(directory, update.fileName), update.newContent);

    private readFile = async (filePath: string): Promise<FileContent> => ({
        fileName: filePath,
        lines: (await fsPromises.readFile(filePath, {encoding: 'utf8'})).split('\n')
    })
}