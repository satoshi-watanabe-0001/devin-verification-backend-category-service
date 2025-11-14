CREATE TABLE product_categories (
    category_code VARCHAR(50) PRIMARY KEY,
    display_name VARCHAR(100) NOT NULL,
    hero_image_url TEXT,
    display_order INTEGER NOT NULL,
    lead_text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    product_id VARCHAR(255) PRIMARY KEY,
    category_code VARCHAR(50) REFERENCES product_categories(category_code),
    manufacturer VARCHAR(100) NOT NULL,
    model_name VARCHAR(255) NOT NULL,
    image_url TEXT,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_storage_options (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(255) REFERENCES products(product_id),
    storage_capacity VARCHAR(50) NOT NULL
);

CREATE TABLE product_color_options (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(255) REFERENCES products(product_id),
    color_name VARCHAR(100) NOT NULL,
    color_code VARCHAR(7) NOT NULL
);

CREATE TABLE campaigns (
    campaign_id VARCHAR(50) PRIMARY KEY,
    campaign_name VARCHAR(100) NOT NULL,
    badge_label VARCHAR(50),
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE product_campaigns (
    product_id VARCHAR(255) REFERENCES products(product_id),
    campaign_id VARCHAR(50) REFERENCES campaigns(campaign_id),
    regular_price INTEGER,
    campaign_price INTEGER,
    installment_months INTEGER,
    installment_monthly_price INTEGER,
    PRIMARY KEY (product_id, campaign_id)
);

CREATE INDEX idx_products_category_code ON products(category_code);
CREATE INDEX idx_product_storage_options_product_id ON product_storage_options(product_id);
CREATE INDEX idx_product_color_options_product_id ON product_color_options(product_id);
CREATE INDEX idx_product_campaigns_product_id ON product_campaigns(product_id);
CREATE INDEX idx_product_campaigns_campaign_id ON product_campaigns(campaign_id);

INSERT INTO product_categories (category_code, display_name, display_order, lead_text) VALUES
('iphone', 'iPhone', 1, 'Latest iPhone models'),
('android', 'Android', 2, 'Android smartphones'),
('refurbished', 'ドコモ認定リユース品', 3, 'Certified refurbished devices'),
('accessories', 'アクセサリ', 4, 'Phone accessories and cases');
