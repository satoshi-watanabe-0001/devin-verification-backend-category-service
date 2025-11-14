INSERT INTO campaigns (campaign_id, campaign_name, badge_label, start_date, end_date) VALUES
('new-year-2024', '新春セール', '新春セール', '2024-01-01 00:00:00', '2024-01-31 23:59:59'),
('spring-campaign', '春の新生活応援', '春の新生活', '2024-03-01 00:00:00', '2024-04-30 23:59:59'),
('summer-sale', '夏のボーナスセール', 'ボーナスセール', '2024-06-01 00:00:00', '2024-07-31 23:59:59'),
('refurbished-special', 'リユース品特価', '特価', '2024-01-01 00:00:00', '2024-12-31 23:59:59');

INSERT INTO products (product_id, category_code, manufacturer, model_name, image_url, description) VALUES
('iphone-15-pro-max', 'iphone', 'Apple', 'iPhone 15 Pro Max', 'https://example.com/iphone15promax.jpg', 'チタニウムデザインの最上位モデル'),
('iphone-15-pro', 'iphone', 'Apple', 'iPhone 15 Pro', 'https://example.com/iphone15pro.jpg', 'プロ仕様のパフォーマンス'),
('iphone-15-plus', 'iphone', 'Apple', 'iPhone 15 Plus', 'https://example.com/iphone15plus.jpg', '大画面で快適な操作'),
('iphone-15', 'iphone', 'Apple', 'iPhone 15', 'https://example.com/iphone15.jpg', 'スタンダードモデル'),
('iphone-14', 'iphone', 'Apple', 'iPhone 14', 'https://example.com/iphone14.jpg', '前モデルのお買い得品');

INSERT INTO products (product_id, category_code, manufacturer, model_name, image_url, description) VALUES
('galaxy-s24-ultra', 'android', 'Samsung', 'Galaxy S24 Ultra', 'https://example.com/galaxys24ultra.jpg', 'Galaxyの最上位モデル'),
('galaxy-s24', 'android', 'Samsung', 'Galaxy S24', 'https://example.com/galaxys24.jpg', 'ハイエンドスマートフォン'),
('xperia-1-vi', 'android', 'Sony', 'Xperia 1 VI', 'https://example.com/xperia1vi.jpg', 'ソニーのフラッグシップモデル'),
('pixel-8-pro', 'android', 'Google', 'Pixel 8 Pro', 'https://example.com/pixel8pro.jpg', 'Googleの最新AI搭載モデル'),
('aquos-r8-pro', 'android', 'Sharp', 'AQUOS R8 Pro', 'https://example.com/aquosr8pro.jpg', 'シャープの高性能カメラ搭載モデル');

INSERT INTO products (product_id, category_code, manufacturer, model_name, image_url, description) VALUES
('refurb-iphone-14-pro', 'refurbished', 'Apple', 'iPhone 14 Pro（リユース品）', 'https://example.com/iphone14pro-refurb.jpg', 'ドコモ認定リユース品・Aランク'),
('refurb-iphone-13', 'refurbished', 'Apple', 'iPhone 13（リユース品）', 'https://example.com/iphone13-refurb.jpg', 'ドコモ認定リユース品・Aランク'),
('refurb-galaxy-s23', 'refurbished', 'Samsung', 'Galaxy S23（リユース品）', 'https://example.com/galaxys23-refurb.jpg', 'ドコモ認定リユース品・Bランク'),
('refurb-xperia-5-iv', 'refurbished', 'Sony', 'Xperia 5 IV（リユース品）', 'https://example.com/xperia5iv-refurb.jpg', 'ドコモ認定リユース品・Aランク');

INSERT INTO products (product_id, category_code, manufacturer, model_name, image_url, description) VALUES
('case-iphone-15-pro', 'accessories', 'Apple', 'iPhone 15 Pro シリコーンケース', 'https://example.com/case-iphone15pro.jpg', '純正シリコーンケース'),
('case-universal-clear', 'accessories', 'Generic', 'クリアケース（汎用）', 'https://example.com/case-clear.jpg', '透明保護ケース'),
('charger-usbc-20w', 'accessories', 'Apple', 'USB-C電源アダプタ 20W', 'https://example.com/charger-20w.jpg', '急速充電対応'),
('cable-usbc-lightning', 'accessories', 'Apple', 'USB-C - Lightningケーブル', 'https://example.com/cable-lightning.jpg', '1mケーブル'),
('screen-protector', 'accessories', 'Generic', '強化ガラスフィルム', 'https://example.com/screen-protector.jpg', '9H硬度ガラスフィルム');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('iphone-15-pro-max', '256GB'),
('iphone-15-pro-max', '512GB'),
('iphone-15-pro-max', '1TB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('iphone-15-pro', '128GB'),
('iphone-15-pro', '256GB'),
('iphone-15-pro', '512GB'),
('iphone-15-pro', '1TB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('iphone-15-plus', '128GB'),
('iphone-15-plus', '256GB'),
('iphone-15-plus', '512GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('iphone-15', '128GB'),
('iphone-15', '256GB'),
('iphone-15', '512GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('iphone-14', '128GB'),
('iphone-14', '256GB'),
('iphone-14', '512GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('galaxy-s24-ultra', '256GB'),
('galaxy-s24-ultra', '512GB'),
('galaxy-s24-ultra', '1TB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('galaxy-s24', '128GB'),
('galaxy-s24', '256GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('xperia-1-vi', '256GB'),
('xperia-1-vi', '512GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('pixel-8-pro', '128GB'),
('pixel-8-pro', '256GB'),
('pixel-8-pro', '512GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('aquos-r8-pro', '256GB');

INSERT INTO product_storage_options (product_id, storage_capacity) VALUES
('refurb-iphone-14-pro', '256GB'),
('refurb-iphone-14-pro', '512GB'),
('refurb-iphone-13', '128GB'),
('refurb-iphone-13', '256GB'),
('refurb-galaxy-s23', '256GB'),
('refurb-xperia-5-iv', '128GB');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('iphone-15-pro-max', 'ナチュラルチタニウム', '#8A8A8A'),
('iphone-15-pro-max', 'ブルーチタニウム', '#4A5568'),
('iphone-15-pro-max', 'ホワイトチタニウム', '#E5E5E5'),
('iphone-15-pro-max', 'ブラックチタニウム', '#2D3748');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('iphone-15-pro', 'ナチュラルチタニウム', '#8A8A8A'),
('iphone-15-pro', 'ブルーチタニウム', '#4A5568'),
('iphone-15-pro', 'ホワイトチタニウム', '#E5E5E5'),
('iphone-15-pro', 'ブラックチタニウム', '#2D3748');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('iphone-15-plus', 'ブラック', '#000000'),
('iphone-15-plus', 'ブルー', '#1E40AF'),
('iphone-15-plus', 'グリーン', '#059669'),
('iphone-15-plus', 'イエロー', '#F59E0B'),
('iphone-15-plus', 'ピンク', '#EC4899');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('iphone-15', 'ブラック', '#000000'),
('iphone-15', 'ブルー', '#1E40AF'),
('iphone-15', 'グリーン', '#059669'),
('iphone-15', 'イエロー', '#F59E0B'),
('iphone-15', 'ピンク', '#EC4899');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('iphone-14', 'ミッドナイト', '#1F2937'),
('iphone-14', 'スターライト', '#F3F4F6'),
('iphone-14', 'ブルー', '#3B82F6'),
('iphone-14', 'パープル', '#9333EA'),
('iphone-14', 'レッド', '#DC2626');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('galaxy-s24-ultra', 'チタニウムグレー', '#6B7280'),
('galaxy-s24-ultra', 'チタニウムブラック', '#111827'),
('galaxy-s24-ultra', 'チタニウムバイオレット', '#7C3AED');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('galaxy-s24', 'オニキスブラック', '#1F2937'),
('galaxy-s24', 'マーブルグレー', '#9CA3AF'),
('galaxy-s24', 'コバルトバイオレット', '#8B5CF6'),
('galaxy-s24', 'アンバーイエロー', '#F59E0B');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('xperia-1-vi', 'ブラック', '#000000'),
('xperia-1-vi', 'プラチナシルバー', '#D1D5DB'),
('xperia-1-vi', 'カーキグリーン', '#65A30D');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('pixel-8-pro', 'オブシディアン', '#1F2937'),
('pixel-8-pro', 'ポーセリン', '#F9FAFB'),
('pixel-8-pro', 'ベイ', '#60A5FA');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('aquos-r8-pro', 'ブラック', '#000000'),
('aquos-r8-pro', 'ブルー', '#2563EB');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('refurb-iphone-14-pro', 'スペースブラック', '#1F2937'),
('refurb-iphone-14-pro', 'シルバー', '#D1D5DB'),
('refurb-iphone-13', 'ミッドナイト', '#1F2937'),
('refurb-iphone-13', 'スターライト', '#F3F4F6'),
('refurb-galaxy-s23', 'ファントムブラック', '#111827'),
('refurb-xperia-5-iv', 'ブラック', '#000000');

INSERT INTO product_color_options (product_id, color_name, color_code) VALUES
('case-iphone-15-pro', 'ブラック', '#000000'),
('case-iphone-15-pro', 'ホワイト', '#FFFFFF'),
('case-iphone-15-pro', 'ブルー', '#3B82F6'),
('case-universal-clear', 'クリア', '#FFFFFF');

INSERT INTO product_campaigns (product_id, campaign_id, regular_price, campaign_price, installment_months, installment_monthly_price) VALUES
('iphone-15-pro-max', 'new-year-2024', 189800, 169800, 36, 4716),
('iphone-15-pro', 'new-year-2024', 159800, 139800, 36, 3883),
('iphone-15-plus', 'spring-campaign', 139800, 129800, 36, 3605),
('iphone-15', 'spring-campaign', 124800, 114800, 36, 3188),
('iphone-14', 'summer-sale', 112800, 99800, 36, 2772);

INSERT INTO product_campaigns (product_id, campaign_id, regular_price, campaign_price, installment_months, installment_monthly_price) VALUES
('galaxy-s24-ultra', 'new-year-2024', 189800, 169800, 36, 4716),
('galaxy-s24', 'spring-campaign', 139800, 124800, 36, 3466),
('xperia-1-vi', 'spring-campaign', 179800, 159800, 36, 4438),
('pixel-8-pro', 'summer-sale', 159800, 139800, 36, 3883),
('aquos-r8-pro', 'summer-sale', 149800, 134800, 36, 3744);

INSERT INTO product_campaigns (product_id, campaign_id, regular_price, campaign_price, installment_months, installment_monthly_price) VALUES
('refurb-iphone-14-pro', 'refurbished-special', 119800, 99800, 24, 4158),
('refurb-iphone-13', 'refurbished-special', 79800, 69800, 24, 2908),
('refurb-galaxy-s23', 'refurbished-special', 89800, 74800, 24, 3116),
('refurb-xperia-5-iv', 'refurbished-special', 69800, 59800, 24, 2491);

INSERT INTO product_campaigns (product_id, campaign_id, regular_price, campaign_price, installment_months, installment_monthly_price) VALUES
('case-iphone-15-pro', 'spring-campaign', 6980, NULL, NULL, NULL),
('case-universal-clear', 'spring-campaign', 1980, NULL, NULL, NULL),
('charger-usbc-20w', 'spring-campaign', 2780, NULL, NULL, NULL),
('cable-usbc-lightning', 'spring-campaign', 2480, NULL, NULL, NULL),
('screen-protector', 'spring-campaign', 1480, NULL, NULL, NULL);
