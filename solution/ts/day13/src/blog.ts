export class Article {
    private readonly name: string;
    private readonly content: string;
    private readonly comments: Comment[];

    constructor(name: string, content: string) {
        this.name = name;
        this.content = content;
        this.comments = [];
    }

    private addCommentWithCreationDate = (text: string, author: string, creationDate: Date): void => {
        const comment = new Comment(text, author, creationDate);

        if (this.comments.some(c => c.isSame(comment))) {
            throw new Error("Comment already exists");
        } else {
            this.comments.push(comment);
        }
    };

    public addComment = (text: string, author: string): void => this.addCommentWithCreationDate(text, author, new Date());
    public getComments = (): Comment[] => this.comments;
}

export class Comment {
    constructor(
        public readonly text: string,
        public readonly author: string,
        public readonly creationDate: Date
    ) {}

    isSame = (otherComment: Comment) => this.text === otherComment.text
        && this.author === otherComment.author
        && this.creationDate.getDate() === otherComment.creationDate.getDate();
}
