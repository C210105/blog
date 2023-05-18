package net.shop2k.blog.entitys;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/*
 * 記事情報
 */

@Data //メソッドを自動的に生成定義 set,get,toString,equals,hashCode
@Entity //データベース内のテーブルにマップ指定
@Table(name= "articles") //DBのarticlesテーブルにマップに指定
public class Articles {
    
    @Id //主キー
    @GeneratedValue(strategy = GenerationType.AUTO) //主キーの値が自動的に生成される定義する
    private Long id;

    @Column(name = "name") //記事の名
    private String name;

    @Column(name = "title") //記事のタイトル 
    private String title;

    @Column(name = "shortTitle") //記事のショートタイトル
    private String shortTitle;

    @Column(name = "updateDay") //記事のアップデート時間
    private LocalDateTime updateDay;

    @Column(name = "urlImage") //写真のリンク
    private String urlImage;

    @Column (name = "hotArticles") //記事のホットのレベル
    private Long hotArticles;

    @ManyToOne(fetch = FetchType.LAZY) //フィーチャクラス間の多対１関係を定義するアノテーション
    @JoinColumn(name = "category_id", nullable = false) //結合列フィールドを定義する、使用されるデータベース内列名を指定、フィルード空白はダメ
    private Categorys category;
}
