import {Article} from '../src/blog';

describe('Article in a blog', () => {
    test('should add valid comment', () => {
        const article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        article.addComment("Amazing article !!!", "Pablo Escobar");
        expect(article.getComments().length).toBeGreaterThan(0);
    });

    test('should add a comment with the given text', () => {
        const article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        const text = "Amazing article !!!";
        article.addComment(text, "Pablo Escobar");

        expect(article.getComments()).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ text })
            ])
        );
    });

    test('should add a comment with the given author', () => {
        const article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        const author = "Pablo Escobar";
        article.addComment("Amazing article !!!", author);

        expect(article.getComments()).toEqual(
            expect.arrayContaining([
                expect.objectContaining({ author })
            ])
        );
    });

    test('should add a comment with the date of the day', () => {
        const article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        article.addComment("Amazing article !!!", "Pablo Escobar");
        expect(article.getComments()[0].creationDate.toDateString()).toBe(new Date().toDateString());
    });

    test('should throw an error when adding existing comment', () => {
        const article = new Article(
            "Lorem Ipsum",
            "consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore"
        );

        article.addComment("Amazing article !!!", "Pablo Escobar");

        expect(() => {
            article.addComment("Amazing article !!!", "Pablo Escobar");
        }).toThrow("Comment already exists");
    });
});
