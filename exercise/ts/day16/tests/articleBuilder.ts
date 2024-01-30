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
        const article = new Article(faker.lorem.sentence(), faker.lorem.paragraph());

        Object.entries(this.comments).forEach(([text, author]) => {
            article.addComment(text, author);
        });

        return article;
    }
}