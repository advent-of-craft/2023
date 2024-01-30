import {Article, Comment, Error} from '../src/blog';
import {faker} from '@faker-js/faker';
import {ArticleBuilder, Author, CommentText} from "./articleBuilder";
import * as E from "fp-ts/Either";
import {pipe} from "fp-ts/function";

describe('Article in a blog', () => {
    let _result: E.Either<Error, Article>;

    test('should add comment in an article', () => {
        given(ArticleBuilder.anArticle());
        when(article => article.addComment(CommentText, Author));
        then(result => assertRight(result, (article) => {
            expect(article.getComments()).toHaveLength(1);
            assertComment(article.getComments()[0], CommentText, Author);
        }));
    });


    test('should add comment in an article containing already one', () => {
        const newComment = faker.word.words(2);
        const newAuthor = faker.word.noun();

        given(ArticleBuilder.anArticle().commented());
        when(article => article.addComment(newComment, newAuthor));
        then(result => assertRight(result, (article) => {
            expect(article.getComments()).toHaveLength(2);
            assertComment(article.getComments()[1], newComment, newAuthor);
        }));
    });

    test('should fail when adding existing comment', () => {
        given(ArticleBuilder.anArticle().commented());
        when(article => article.addComment(CommentText, Author));
        then(result => {
            expect(E.isLeft(result)).toBeTruthy();

            if (E.isLeft(result)) {
                expect(result.left).toStrictEqual({message: "Comment already exists"});
            }
        });
    });

    const assertRight = (result: E.Either<Error, Article>, assertArticle: (article: Article) => void) => {
        expect(E.isRight(result)).toBeTruthy();
        if (E.isRight(result)) assertArticle(result.right);
    }

    const assertComment = (comment: Comment, expectedText: string, expectedAuthor: string) => {
        expect(comment.text).toBe(expectedText);
        expect(comment.author).toBe(expectedAuthor);
    }

    const given = (articleBuilder: ArticleBuilder) => _result = E.right(articleBuilder.build());
    const when = (act: (article: Article) => E.Either<Error, Article>) => pipe(
        _result,
        E.bind("act on article", article => _result = act(article))
    )
    const then = (act: (article: E.Either<Error, Article>) => void) => act(_result);
});