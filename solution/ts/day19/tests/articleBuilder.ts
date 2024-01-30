import {Article} from "../src/blog";
import {faker} from "@faker-js/faker";
import * as E from "fp-ts/Either";

export const Author = "Pablo Escobar";
export const CommentText = "Amazing article !!!";

export class ArticleBuilder {
    private comments: Record<string, string> = {};
    static anArticle = (): ArticleBuilder => new ArticleBuilder();

    commented(): ArticleBuilder {
        this.comments[CommentText] = Author;
        return this;
    }

    build(): Article {
        let article = Article.create(faker.lorem.sentence(), faker.lorem.paragraph());
        Object.entries(this.comments).forEach(([text, author]) => {
            let result = article.addComment(text, author);
            if (E.isRight(result)) article = result.right;
        });
        return article;
    }
}