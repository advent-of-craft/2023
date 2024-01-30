export class Article {
    private constructor(private readonly name: string,
                        private readonly content: string,
                        private readonly comments: Comment[]) {
    }

    public static create = (name: string, content: string): Article => new Article(name, content, []);

    public addComment = (text: string, author: string): Article =>
        this.addCommentWithCreationDate(text, author, new Date());

    public getComments = (): Comment[] => this.comments;

    private addCommentWithCreationDate = (text: string, author: string, creationDate: Date): Article => {
        const comment = new Comment(text, author, creationDate);

        if (this.comments.some(c => c.isSame(comment))) {
            throw new Error("Comment already exists");
        }
        return new Article(this.name, this.content, [...this.comments, comment]);
    };
}

export class Comment {
    constructor(
        public readonly text: string,
        public readonly author: string,
        public readonly creationDate: Date
    ) {
    }

    isSame = (otherComment: Comment) => this.text === otherComment.text
        && this.author === otherComment.author
        && this.creationDate.getDate() === otherComment.creationDate.getDate();
}
