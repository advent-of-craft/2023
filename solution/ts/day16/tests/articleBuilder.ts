import {Article} from "../src/blog";
import {faker} from "@faker-js/faker";

export const Author = "Pablo Escobar";
export const CommentText = "Amazing article !!!";

export class ArticleBuilder {
    private comments: Record<string, string> = {};

    static anArticle(): ArticleBuilder {
        return new ArticleBuilder();
    }

    commented(): ArticleBuilder {
        this.comments[CommentText] = Author;
        return this;
    }

    build(): Article {
        let article = Article.create(faker.lorem.sentence(), faker.lorem.paragraph());
        Object.entries(this.comments).forEach(([text, author]) => article = article.addComment(text, author));
        return article;
    }
}