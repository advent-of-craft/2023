import {Article, Comment} from '../src/blog';
import {faker} from '@faker-js/faker';
import {ArticleBuilder, Author, CommentText} from "./articleBuilder";

describe('Article in a blog', () => {
    let article: Article;

    test('should add comment in an article', () => {
        given(ArticleBuilder.anArticle());
        when(article => article.addComment(CommentText, Author));
        then(article => {
            expect(article.getComments()).toHaveLength(1);
            assertComment(article.getComments()[0], CommentText, Author);
        });
    });

    test('should add comment in an article containing already one', () => {
        const newComment = faker.word.words(2);
        const newAuthor = faker.word.noun();

        given(ArticleBuilder.anArticle().commented());
        when(article => article.addComment(newComment, newAuthor));
        then(article => {
            expect(article.getComments()).toHaveLength(2);
            assertComment(article.getComments()[1], newComment, newAuthor);
        });
    });

    test('should fail when adding existing comment', () => {
        given(ArticleBuilder.anArticle());
        article = article.addComment(CommentText, Author);

        expect(() => article.addComment(CommentText, Author))
            .toThrow("Comment already exists");
    });

    const assertComment = (comment: Comment, expectedText: string, expectedAuthor: string) => {
        expect(comment.text).toBe(expectedText);
        expect(comment.author).toBe(expectedAuthor);
    }

    const given = (articleBuilder: ArticleBuilder) => article = articleBuilder.build();
    const when = (act: (article: Article) => Article) => article = act(article);
    const then = (act: (article: Article) => void) => act(article);
});