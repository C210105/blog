package net.shop2k.blog.entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


/*
 * 商品情報
 */

@Data //メソッドを自動的に生成定義
@Entity //DB内にマップに定義
@Table (name = "products") //productsテーブルをマップ指定
public class Products {
    
    @Id //主キー
    @GeneratedValue(strategy = GenerationType.AUTO) //主キー値を自動的に生成定義
    private Long id;
    
    @Column(name = "name") //商品名
    private String name;

    @Column(name = "urlImage") //写真リンク
    private String urlImage;
}
