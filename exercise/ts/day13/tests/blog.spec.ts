import {Article} from '../src/blog';

describe('Article in a blog', () => {
    let article: Article;
    const commentText = "Amazing article !!!";
    const author = "Pablo Escobar";

    beforeEach(() => {
        article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );
    })

    test('should add comment in an article', () => {
        article.addComment(commentText, author);

        expect(article.getComments()).toHaveLength(1);

        const comment = article.getComments()[0];
        expect(comment.text).toBe(commentText);
        expect(comment.author).toBe(author);
    });

    test('should add comment in an article containing already one', () => {
        const newComment = "Finibus Bonorum et Malorum";
        const newAuthor = "Al Capone";

        article.addComment(commentText, author);
        article.addComment(newComment, newAuthor);

        const lastComment = article.getComments()[1];
        expect(lastComment.text).toBe(newComment);
        expect(lastComment.author).toBe(newAuthor);
    });

    test('should fail when adding existing comment', () => {
        article.addComment(commentText, author);

        expect(() => article.addComment(commentText, author))
            .toThrow("Comment already exists");
    });
});
